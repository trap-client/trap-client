/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

const axios = require("axios").default;
const FormData = require("form-data");
const fs = require("fs");

const branch = process.argv[2];
const compareUrl = process.argv[3];
const success = process.argv[4] === "true";

function send(version, number) {
    axios.get(compareUrl).then(res => {
        let description = "";

        description += "**Branch:** " + branch;
        description += "\n**Status:** " + (success ? "success" : "failure");

        let changes = "\n\n**Changes:**";
        let hasChanges = false;
        for (let i in res.data.commits) {
            let commit = res.data.commits[i];

            changes += "\n- [`" + commit.sha.substring(0, 7) + "`](https://github.com/trap-client/trap-client/commit/" + commit.sha + ") *" + commit.commit.message + "*";
            hasChanges = true;
        }
        if (hasChanges) description += changes;

        if (success) {
            description += "\n\n**Download:** [trap-client-" + version + "-" + number + "](https://github.com/trap-client/trap-client/releases/tag/" + number + ")";
        }

        const webhook = {
            username: "Dev Builds",
            avatar_url: "https://meteorclient.com/icon.png",
            embeds: [
                {
                    title: "trap client client v" + version + " build #" + number,
                    description: description,
                    url: "https://github.com/trap-client/trap-client",
                        color: success ? 2672680 : 13117480
                }
            ]
        };

        axios.post(process.env.DISCORD_WEBHOOK, webhook);
    });
}

if (success) {
    let jar = "";
    fs.readdirSync("../../build/libs").forEach(file => {
        if (!file.endsWith("-all.jar") && !file.endsWith("-sources.jar")) jar = "../../build/libs/" + file;
    });

    let form = new FormData();
    form.append("file", fs.createReadStream(jar));
}

/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

const axios = require("axios").default;

axios.get("https://meteorclient.com/api/stats").then(res => {
    console.log("::set-output name=number::" + (parseInt(res.data.devBuild) + 1));
});

/*
 * This file is part of the Trap Client distribution (https://github.com/trap-client/trap-client).
 * Copyright (c) Vince#1145.
 */

const axios = require("axios").default;

axios.get("https://meteorclient.com/api/stats").then(res => {
    console.log("::set-output name=number::" + (parseInt(res.data.devBuild) + 1));
});

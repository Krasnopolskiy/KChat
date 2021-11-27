import {ContentBoxFluid} from "../../../components/ContentBoxes";
import {Box, IconButton, TextField} from "@mui/material";
import {SendRounded} from "@mui/icons-material";
import * as React from "react";

export const MessageInput = () =>
    <ContentBoxFluid padding={2}>
        <Box paddingX={2}>
            <TextField fullWidth={true} color={"primary"} id="outlined-basic" label="Message" variant="standard"
                       InputProps={{endAdornment: <IconButton color={"primary"}><SendRounded/></IconButton>}}/>
        </Box>
    </ContentBoxFluid>
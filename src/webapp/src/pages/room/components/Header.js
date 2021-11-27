import {ContentBoxFluid} from "../../../components/ContentBoxes";
import {IconButton, Stack, Typography} from "@mui/material";
import {ArrowBackRounded, SettingsRounded} from "@mui/icons-material";
import * as React from "react";

export const Header = ({name}) =>
    <ContentBoxFluid padding={2}>
        <Stack direction={"row"} justifyContent={"space-between"} minWidth={"min-content"}>
            <IconButton color="primary" size={"small"} href={"/rooms"}>
                <ArrowBackRounded/>
            </IconButton>
            <Typography variant={"h6"} display={"block"}>{name}</Typography>
            <IconButton disabled color="primary" size={"small"}>
                <SettingsRounded/>
            </IconButton>
        </Stack>
    </ContentBoxFluid>
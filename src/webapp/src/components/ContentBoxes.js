import * as React from 'react';
import {Box} from "@mui/material"

const ContentBoxFluid = props =>
    <Box padding={props.padding || 3} borderRadius={4} boxShadow={3}>{props.children}</Box>

const ContentBoxWrap = props =>
    <Box padding={props.padding || 3} borderRadius={4} boxShadow={3} maxWidth={"max-content"}>{props.children}</Box>

export {ContentBoxFluid, ContentBoxWrap}
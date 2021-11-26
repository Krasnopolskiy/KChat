import * as React from 'react';
import {Box} from "@mui/material"

const ContentBoxFluid = ({children, padding}) =>
    <Box padding={padding || 3} borderRadius={4} boxShadow={3}>{children}</Box>

const ContentBoxWrap = ({children, padding}) =>
    <Box padding={padding || 3} borderRadius={4} boxShadow={3} maxWidth={"max-content"}>{children}</Box>

export {ContentBoxFluid, ContentBoxWrap}
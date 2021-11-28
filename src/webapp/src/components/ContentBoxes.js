import * as React from 'react'
import {Box} from '@mui/material'

const ContentBoxFluid = ({children, padding, sx}) =>
    <Box padding={padding || 3} borderRadius={4} boxShadow={3} sx={sx}>{children}</Box>

const ContentBoxWrap = ({children, padding, sx}) =>
    <Box padding={padding || 3} borderRadius={4} boxShadow={3} maxWidth={'max-content'} sx={sx}>{children}</Box>

export {ContentBoxFluid, ContentBoxWrap}
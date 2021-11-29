import * as React from 'react'
import {Box, Stack, Typography} from '@mui/material'
import {ContentBoxWrap} from '../../../components/ContentBoxes'
import {ChipWithAvatar} from '../../../components/StringAvatar'


export const Message = ({author, text}) =>
    <Box display={'flex'}>
        <ContentBoxWrap padding={1}>
            <Stack direction={'row'} gap={2} paddingRight={2} alignItems={'baseline'}>
                <ChipWithAvatar name={author}/>
                <Typography>{text}</Typography>
            </Stack>
        </ContentBoxWrap>
    </Box>

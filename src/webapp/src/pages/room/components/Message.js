import * as React from 'react'
import {Box, Stack, Typography} from '@mui/material'
import {ContentBoxWrap} from '../../../components/ContentBoxes'
import {ChipWithAvatar} from '../../../components/StringAvatar'


export const Message = ({justifyContent, author, text}) =>
    <Box display={'flex'} justifyContent={justifyContent || 'left'}>
        <ContentBoxWrap padding={1}>
            <Stack direction={'row'} gap={2} alignItems={'center'} paddingRight={2}>
                <ChipWithAvatar name={author}/>
                <Typography>{text}</Typography>
            </Stack>
        </ContentBoxWrap>
    </Box>

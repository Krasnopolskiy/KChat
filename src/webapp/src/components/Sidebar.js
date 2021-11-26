import {Avatar, IconButton, Stack} from "@mui/material";
import {ContentBoxWrap} from "./ContentBoxes";
import {EmailRounded, HomeRounded, LogoutRounded, MarkEmailUnreadRounded} from "@mui/icons-material";
import {stringAvatar} from "./StringAvatar";
import * as React from 'react';

export const Sidebar = props =>
    <Stack gap={2}>
        <ContentBoxWrap padding={2}>
            <Avatar {...stringAvatar(props.name || 'John Doe')} />
        </ContentBoxWrap>
        <ContentBoxWrap padding={2}>
            <Stack gap={2}>
                <IconButton href={"/home"} color="primary">
                    <HomeRounded/>
                </IconButton>
                <IconButton href={"/chats"} color="primary">
                    <EmailRounded/>
                </IconButton>
                <IconButton href={"/unread"} color="primary">
                    <MarkEmailUnreadRounded/>
                </IconButton>
            </Stack>
        </ContentBoxWrap>
        <ContentBoxWrap padding={2}>
            <IconButton href={"/logout"} color="primary">
                <LogoutRounded/>
            </IconButton>
        </ContentBoxWrap>
    </Stack>
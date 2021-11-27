import {Avatar, Container, IconButton, Stack} from "@mui/material";
import {ContentBoxWrap} from "./ContentBoxes";
import {EmailRounded, HomeRounded, LogoutRounded, MarkEmailUnreadRounded} from "@mui/icons-material";
import * as React from "react";
import axios from "axios";
import {WithWaiting} from './WithWaiting'


export class Sidebar extends React.Component {
    constructor(props) {
        super(props)
        this.username = null
    }

    stringToColor = (string) => {
        let hash = 0
        let i
        for (i = 0; i < string.length; i += 1) {
            hash = string.charCodeAt(i) + ((hash << 5) - hash)
        }
        let color = '#'
        for (i = 0; i < 3; i += 1) {
            const value = (hash >> (i * 8)) & 0xff
            color += `00${value.toString(16)}`.substr(-2)
        }
        return color;
    }

    getUserName = () =>
        axios.get(`/api/user`, {withCredentials: true})
            .then(res => {
                this.username = res.data.user.name
            })

    StringAvatar = () =>
        <Avatar sx={{bgcolor: this.stringToColor(this.username), color: '#fff'}}>
            {this.username.split(' ').slice(0, 2).map(el => el[0].toUpperCase()).join('')}
        </Avatar>

    render = () => (
        <WithWaiting pending={this.getUserName}>
            <Container>
                <Stack gap={2} position={"fixed"}>
                    <ContentBoxWrap padding={2}>
                        <this.StringAvatar/>
                    </ContentBoxWrap>
                    <ContentBoxWrap padding={2}>
                        <Stack gap={2}>
                            <IconButton href={"/home"} color="primary">
                                <HomeRounded/>
                            </IconButton>
                            <IconButton href={"/rooms"} color="primary">
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
            </Container>
        </WithWaiting>
    )
}
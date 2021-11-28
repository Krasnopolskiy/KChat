import * as React from 'react'
import {Avatar, Chip} from '@mui/material'

const stringToColor = (string) => {
    if (string === null) return '#333'
    let hash = 0
    for (let i = 0; i < string.length; i += 1)
        hash = string.charCodeAt(i) + ((hash << 5) - hash)
    let color = '#'
    for (let i = 0; i < 3; i += 1) {
        const value = (hash >> (i * 8)) & 0xff
        color += `00${value.toString(16)}`.substr(-2)
    }
    return color
}

const trimUserName = (name) => {
    if (name === null) return ''
    return name.split(' ').slice(0, 2).map(el => el[0].toUpperCase()).join('')
}

export const StringAvatar = ({name}) =>
    <Avatar sx={{bgcolor: stringToColor(name), color: '#fff'}}>
        {trimUserName(name)}
    </Avatar>

export const ChipWithAvatar = ({name}) =>
    <Chip avatar={
        <Avatar sx={{bgcolor: stringToColor(name)}}>{trimUserName(name)}</Avatar>
    } label={name}/>
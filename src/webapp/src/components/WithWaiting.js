import {Backdrop, CircularProgress} from "@mui/material";
import {useEffect, useState} from "react";
import * as React from "react";

const LoadingBackdrop = () =>
    <Backdrop open={true} sx={{color: '#fff'}}>
        <CircularProgress color={"inherit"}/>
    </Backdrop>

export const WithWaiting = ({children, pending}) => {
    const [isLoading, setLoading] = useState(true)

    useEffect(() => {
        const waitForPending = async () => {
            try {
                await pending()
            } catch {
            }
        }

        waitForPending().then(() => setLoading(false))
    })

    return isLoading ? <LoadingBackdrop/> : children
}
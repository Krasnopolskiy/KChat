import * as React from 'react';
import ReactDOM from 'react-dom';
import {Grid, Stack, Typography} from "@mui/material";
import {ContentBoxWrap} from "../../components/ContentBoxes";
import Cookies from "js-cookie"
import Button from "@mui/material/Button";

const ErrorPage = () => {
    const goBack = window.history.back.bind(window.history)
    let error = Cookies.get("error") || "error=%23sNothing+there"
    error = error.substring(error.indexOf('%23s') + 4).replace('+', ' ')
    Cookies.remove("error")
    return (
        <Grid container justifyContent={"center"} padding={4}>
            <ContentBoxWrap>
                <Stack gap={4}>
                    <Typography variant={"h2"} textAlign={"center"}>An error has occurred</Typography>
                    <Typography variant={"h4"} color="error.main">{error}</Typography>
                    <Button variant={"contained"} onClick={goBack}>Go back</Button>
                </Stack>
            </ContentBoxWrap>
        </Grid>
    )
}

ReactDOM.render(<ErrorPage/>, document.querySelector('#app'));
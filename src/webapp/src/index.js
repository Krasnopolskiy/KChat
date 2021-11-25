import * as React from 'react';
import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';
import {CssBaseline, Grid, Stack, Typography} from "@mui/material";
import {ContentBoxWrap} from "./components/ContentBoxes";

const IndexPage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent="center" padding={4}>
            <ContentBoxWrap>
                <Typography variant={"h1"} textAlign={"center"} gutterBottom>
                    KChat
                </Typography>
                <Typography variant={"h4"} textAlign={"center"}>
                    Self-hosted websockets chat app, written in Ktor and React
                </Typography>
                <Stack direction={"row"} marginTop={4} gap={2}>
                    <Button variant={"contained"} href={"/authenticate"} size={"large"}>Login</Button>
                    <Button variant={"outlined"} href={"/authenticate"} size={"large"}>Register</Button>
                </Stack>
            </ContentBoxWrap>
        </Grid>
    </React.Fragment>

ReactDOM.render(<IndexPage/>, document.querySelector('#app'));
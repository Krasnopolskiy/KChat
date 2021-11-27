import {WithWaiting} from "./WithWaiting";
import {Container, Grid} from "@mui/material";
import {Sidebar} from "./Sidebar";
import * as React from "react";

export const AppContainer = ({children, pending}) =>
    <WithWaiting pending={pending}>
        <Grid container padding={2}>
            <Grid item xs={1}><Sidebar/></Grid>
            <Grid item xs={11}>
                <Container>{children}</Container>
            </Grid>
        </Grid>
    </WithWaiting>
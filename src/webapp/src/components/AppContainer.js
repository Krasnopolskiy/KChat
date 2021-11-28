import * as React from 'react'
import {Container, Grid} from '@mui/material'
import {Sidebar} from './Sidebar'

export const AppContainer = ({children}) =>
    <Grid container padding={2}>
        <Grid item xs={1}>
            <Sidebar/>
        </Grid>
        <Grid item xs={11}>
            <Container>
                {children}
            </Container>
        </Grid>
    </Grid>
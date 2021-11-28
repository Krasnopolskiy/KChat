import * as React from 'react'
import ReactDOM from 'react-dom'
import {Button, CssBaseline, Grid, Typography} from '@mui/material'
import {ContentBoxWrap} from '../../components/ContentBoxes'

const IndexPage = () =>
    <React.Fragment>
        <CssBaseline/>
        <Grid container justifyContent='center' padding={4}>
            <ContentBoxWrap>
                <Typography variant={'h1'} textAlign={'center'} gutterBottom>
                    KChat
                </Typography>
                <Typography variant={'h4'} textAlign={'center'}>
                    Self-hosted websockets chat app, written in Ktor and React
                </Typography>
                <Button variant={'contained'} href={'/login'} size={'large'}
                        sx={{display: 'flex', width: 'max-content', mt: 4, mx: 'auto'}}>
                    Start messaging
                </Button>
            </ContentBoxWrap>
        </Grid>
    </React.Fragment>

ReactDOM.render(<IndexPage/>, document.querySelector('#app'));
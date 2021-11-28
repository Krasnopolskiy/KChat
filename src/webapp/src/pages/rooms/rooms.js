import * as React from 'react'
import ReactDOM from 'react-dom'
import {Grid, Typography} from '@mui/material'
import {ContentBoxFluid} from '../../components/ContentBoxes'
import {AppContainer} from '../../components/AppContainer'
import {RoomGrid} from './components/RoomGrid'


export const RoomsPage = () =>
    <AppContainer>
        <ContentBoxFluid>
            <Grid gap={2}>
                <Typography variant={'h2'} textAlign={'center'}>My rooms</Typography>
                <RoomGrid/>
            </Grid>
        </ContentBoxFluid>
    </AppContainer>


ReactDOM.render(<RoomsPage/>, document.querySelector('#app'));
import * as React from 'react';
import {Box, Button, Card, CardActions, CardContent, Typography} from '@mui/material';

export const RoomCard = ({name, code}) =>
    <Box padding={2} width={'max-content'}>
        <Card sx={{paddingX: 2}}>
            <CardContent>
                <Typography variant={'h5'}>{name}</Typography>
            </CardContent>
            <CardActions>
                <Button href={`/room/${code}`}>Enter</Button>
            </CardActions>
        </Card>
    </Box>

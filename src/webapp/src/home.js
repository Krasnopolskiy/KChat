import * as React from 'react';
import ReactDOM from 'react-dom';
import Button from '@mui/material/Button';

function HomePage() {
    return <Button variant="contained">Hello Home</Button>;
}

ReactDOM.render(<HomePage/>, document.querySelector('#app'));
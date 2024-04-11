import React from 'react'
import {CircularProgress} from '@mui/material';
import './LoadingComponent.css'
function LoadingComponent({isLoading}) {
  return (
    <>
    {isLoading &&  <div className='loadingContainer'>

          
        </div>}
        {
            isLoading && <div className='loading' >  
            <CircularProgress  size={'50px'}/>
        </div>
        }
    </>
    
  )
}

export default LoadingComponent
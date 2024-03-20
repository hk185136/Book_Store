import React from 'react'
import './Rating.css';
function Rating(props) {
  return (
    <div className='rating'>
        {[...Array(props.count)].map((star) => {        
        return (         
          <span className="star-active">&#9733;</span>        
        );
      })} 
        
        {[...Array(5-props.count)].map((star) => {        
        return (         
          <span className="star-inactive">&#9733;</span>        
        );
      })}
    </div>
  )
}

export default Rating
import React, { useState } from 'react'
import './Book.css'
function Book(props) {
    const [isAdded,setIsAdded] = useState(false);

  return (
    <div className='book-card'>
        <div className='book-img-container'>
            <img src={props.image} alt="No image" className='book-img'/>
        </div>
        <div className='book-details'>
            <p className='book-name'>{props.name}</p>
            <p className='author-name'>By author : {props.author}</p>
            <p className='price'>&#8377;{props.price}</p>
            <button className='buy-button'>Buy</button>
            {(isAdded)?(<img  onClick={()=>setIsAdded(false)} className='cart-img' src="/remove-from-cart.svg" alt="" />):(<img  onClick={()=>setIsAdded(true)} className='cart-img' src="/add-to-cart.png" alt="" />)}
        </div>
        
    </div>
  )
}

export default Book
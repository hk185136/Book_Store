import React from 'react'

import CartBooks from '../../components/cartBooks/CartBooks';
import './Cart.css';
function Cart() {
  return (
      <div className='cart'>
        
        <CartBooks/>
        <div>
          <h1>Order summary</h1>
          <p>Total items : </p>
          <p>Total cost : </p>
        </div>
      </div>
      
    
  )
}

export default Cart
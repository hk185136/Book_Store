import React from 'react'
import CartBook from '../cartBook/CartBook'
import './CartBooks.css'
function CartBooks({cartItems,setCartItems}) {
  async function deleteItem(id){
    //api call
    //setCartItems
  }

  return (
    <div className='card-grid'>
      {
        // cartItems.map((cartItem) => <CartBook cartItem = {cartItem} deleteItem = {deleteItem} />)
      }
      <CartBook image='https://cdn.carnegielearning.com/assets/teaser-images/alg1-se-v3.png' name='Mathematics' author='hemanth' price = {1000}/>
    </div>
  )
}

export default CartBooks
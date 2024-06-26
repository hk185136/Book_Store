import React from 'react'
import CartBook from '../cartBook/CartBook'
import './CartBooks.css';
import axios from 'axios';
import { toast } from 'react-toastify';
import { urls } from '../../api';
function CartBooks({cartItems,setCartItems,setTotal}) {
  
  async function deleteItem(id){
    try{
      // Remove the book from the cart.
      await axios.delete(urls.cart.removeFromCart + id);
      const newCartItems = cartItems.filter((item)=>item.id!==id);
      setCartItems(newCartItems);
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  return (
    <div className='card-grid'>
      {
        cartItems.map((cartItem) => 
          <CartBook 
          key={cartItem.id} 
          cartItem = {cartItem} 
          deleteItem = {deleteItem} 
          setTotal = {setTotal}
          setCartItems = {setCartItems}
          />
        )
      }
    </div>
  )
}

export default CartBooks
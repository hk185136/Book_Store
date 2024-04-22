import React, { useEffect, useState } from 'react'
import './CartBook.css';
import axios from 'axios';
import { toast } from 'react-toastify';
import { urls } from '../../api';
function CartBook({cartItem,deleteItem,setTotal,setCartItems}) {
    const [qty,setQty] = useState(cartItem.quantity);
    const [availableQuantity,setAvailableQuantity] = useState();
    useEffect(()=>{
        async function getBook(){
            try{
                // Get the latest copy of the book that is in the cart.
                const res = await axios.get(urls.book.get+cartItem.book.id);
                if(res.status == 200){
                    setAvailableQuantity(res.data.availableQuantity);
                }
            }
            catch(e){
                const msg = e?.response?.data?.message;
                if(msg === 'No value present'){
                    setAvailableQuantity(0);
                }
                else{
                    toast.error((e?.response?.data?.message) || (e.message));
                }
            }
        }
        getBook();
    },[])
    useEffect(()=>{
        async function setQuantity(){
            // get latest quantity of cart item.
            const res = await axios.get(urls.cart.get+cartItem.id);
            setQty(res.data.quantity);
        }
        setQuantity();
    },[])
    async function increment(){
         setQty(prev=>prev+1)
         setTotal(prev=>prev+cartItem.book.price)
        try{
            // Increase the quantity in the cart.
            await axios.put(urls.cart.ChangeQty+cartItem.id+'/increase',cartItem)
        }
        catch(e){
            toast.error((e?.response?.data?.message) || (e.message));
        }
       
    }
    async function decrement(){
        setQty(prev=>prev-1)  
        setTotal(prev=>prev-cartItem.book.price)
        try{
            // Decrease the quantity in the cart.
            await axios.put(urls.cart.ChangeQty+cartItem.id+'/decrease',cartItem)
        }
        catch(e){
            toast.error((e?.response?.data?.message) || (e.message));
        }
    }
  return (
    <div className='book-card'>

        <div className='book-img-container'>
            <img src={cartItem.book.url} alt="No image" className='book-img'/>
        </div>

        <div className='book-details'>
            <p className='book-name'>{cartItem.book.title}</p>
            <p className='author-name'>By author : {cartItem.book.author}</p>
            <p className='price'>&#8377;{cartItem.book.price}</p>
            {
                (cartItem.book.availableQuantity===0)?
                    (<p>This book is out of stock</p>) :
                (<>
            
                    {availableQuantity === 0 && <p>This book is no longer available</p>}
                    {availableQuantity>0 && 
                    <>
                        <div className='quantity'>
                            <p>Qty : </p>
                            {(qty>1) && <button className='qty-controller' 
                            onClick={()=>{
                                if(qty>0)
                                    decrement();
                            }}>
                            -</button>}
                
                            {(qty>availableQuantity)?availableQuantity:qty}

                            {qty<availableQuantity && <button className='qty-controller' 
                            onClick={()=>{
                                if(qty<availableQuantity){
                                    increment();
                                }
                            }}>+</button>}
                        
                        </div>
                    </>
                    }
                </>)
            }
            {qty>0 && availableQuantity>0 && 
                <div>
                    <p className='subtotal'>Sub total : 
                    <span 
                    style={{color : 'green'}}
                    >
                        &#8377;{cartItem.book.price*((qty>availableQuantity)?availableQuantity:qty)}
                    </span>
                    </p>
                </div>
            }
            <button 
            className='remove-from-cart-button' 
            onClick={()=>deleteItem(cartItem.id)}
            >Remove from cart
            </button>
        </div> 
    </div>
  )
}

export default CartBook
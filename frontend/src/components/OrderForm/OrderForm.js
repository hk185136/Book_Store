import React, { useReducer, useState } from 'react'
import Modal from '../modal/Modal'
import axios from 'axios';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import { CircularProgress } from '@mui/material';

function OrderForm({setIsOpen,book}) {
    const user = useSelector(state=>state);
    const [address,setAddress] = useState(getAddr());
    const [quantity,setQuantity] = useState(1);
    const [isLoading,setIsLoading] = useState(false);
    function getAddr(){
        if(user && user.address && user.address !==''){
            return user.address;
        }
        return ''
    }
    async function handleBuy(e){
      try{
        e.preventDefault();
        setIsLoading(true);
        const body = {
          book : book,
          user : {
            username : user.name,
            address : address,
            pno: user.pno
          },
          quantity : quantity
        }
        // Add the book to orders of the current user.
        const res = await axios.post('http://localhost:8080/api/item/addToOrder',body);
        setIsLoading(false);
        setIsOpen(false);
        if(res.status === 200){
          toast.success("Order placed successfully");
          const newBook = book;
          newBook.availableQuantity = newBook.availableQuantity - quantity;
          // As book is ordered, decrease it's available quantity.
          await axios.put(`http://localhost:8080/api/admin/books/${book.id}`,newBook);
        }
      }
      catch(e){
        setIsLoading(false);
        setIsOpen(false);
        toast.error((e?.response?.data?.message) || (e.message));
      }
     
    }
  return (
    <Modal setIsOpen = {setIsOpen}>
        <form className='add-book-form'>
            {(address==='') && <p>Your account does not have an address</p>}
            <label htmlFor="">Address</label>

            <textarea 
              cols="30" 
              rows="10" 
              value={address} 
              onChange={(e)=>setAddress(e.target.value)}
            >
            </textarea>

            <div className='quantity'>
              <p>Qty : </p>
              
              {(quantity>1) &&  <button className='qty-controller' 
                onClick={(e)=>{
                  e.preventDefault();
                  if(quantity>1)
                      setQuantity(prev=>prev-1)
                }}
                >
                -</button>}
            
              {quantity}

              {quantity<book.availableQuantity && <button className='qty-controller' 
                onClick={(e)=>{
                  e.preventDefault();
                  if(quantity<book.availableQuantity){
                    setQuantity(prev=>prev+1)
                  }
                }}
              >+
              </button>}
            </div>
            {isLoading ? <CircularProgress/> : 
              <button 
              className='buy-button' 
              style={{margin:0}} 
              onClick={(e)=>handleBuy(e)}
              >
                Buy
              </button>
            }
            
        </form>
    </Modal>
  )
}

export default OrderForm
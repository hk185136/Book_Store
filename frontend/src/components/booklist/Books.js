import React, { useEffect, useState } from 'react'
import Book from '../book/Book';
import './Books.css'
import Sidebar from '../sidebar/Sidebar';
import axios from 'axios';
import { toast } from 'react-toastify';
import LoadingComponent from '../Loading/LoadingComponent';
function Books({books,setBooks,cartItems,setCartItems}) {
  const [genre,setGenre] = useState('');
  const [priceRange,setPriceRange] = useState({
    min:0,
    max:20000
  })
  const [isLoading,setIsLoading] = useState(false);
  useEffect(()=>{
   
    async function filter(){
    
      try{
        if(genre==''){
          setIsLoading(true);
          const res = await axios.get(`http://localhost:8080/api/user/books/`);

          if(res.status==200){
            const res2 = await axios.get(`http://localhost:8080/api/user/books/pricerange/${priceRange.min}/${priceRange.max}`);
            const arr1 = res.data;
            const arr2 = res2.data;
            const newBooks = arr1.filter(book1=>{
              return arr2.some(book2=>{
                return book1.id==book2.id;
              })
            })
            // console.log(newBooks)
           
            console.log('loading')
            setBooks(newBooks);
            setIsLoading(false);
          }
        }
        else{
          setIsLoading(true);
          const res = await axios.get(`http://localhost:8080/api/user/books/genre/${genre}`);

          if(res.status==200){
            const res2 = await axios.get(`http://localhost:8080/api/user/books/pricerange/${priceRange.min}/${priceRange.max}`);
            const arr1 = res.data;
            const arr2 = res2.data;
            const newBooks = arr1.filter(book1=>{
              return arr2.some(book2=>{
                return book1.id==book2.id;
              })
            })
            // console.log(newBooks)
           
            console.log('loading')
            setBooks(newBooks);
            setIsLoading(false);
          }
        }
      }
      catch(e){
        toast.error((e?.response?.data?.message) || (e.message));
      }
    }
    filter();
  },[genre,priceRange])
  function isInCart(id) {
    // console.log('isInCart runnig');
    // console.log(cartItems)
    for(let cartItem of cartItems){
      if(cartItem.book.id == id){
        return true;
      }
    }
    return false;
  }
  function findInCart(id) {
    for(let cartItem of cartItems){
      if(cartItem.book.id == id){
        return cartItem.id;
      }
    }
    return undefined;
  }
  async function deleteBook(book1){
    try{
      // console.log(book1.id)
      const url = 'http://localhost:8080/api/admin/books/' + book1.id;
      const res = await axios.delete(url);
      if(res.status == 200){
        const newBooks = books.filter((book)=>book1.id!=book.id);
        setBooks(newBooks);
      }
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  async function editBook(id,book1){
    try{
      const url = 'http://localhost:8080/api/admin/books/' + id;
      // console.log("pointed")
      // console.log(book1)
      const res = await axios.put(url,book1);
      // console.log(res);
      if(res.status == 200){
        const newBooks = books.map((book)=>(book.id===id)?book1:book);
        setBooks(newBooks)
      }
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  return (
    <>
    <Sidebar setGenre = {setGenre} priceRange = {priceRange} setPriceRange = {setPriceRange}/>

    
    (<div className='books'>
      <LoadingComponent isLoading={isLoading}/>
      {books.length>0?(<>
        <div className='books-grid'>
      {
          books.map((book)=><Book key={book.id} isInCart = {isInCart(book.id)} cartId = {findInCart(book.id)} book = {book} deleteBook = {deleteBook} editBook = {editBook} cartItems = {cartItems} setCartItems = {setCartItems}/>)
        }
      </div>
      </>):((!isLoading) &&  <img src='/no-search-found.png'></img>)}
      
     
    </div>)
    </>
    
  )
}

export default Books
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
          // Get all the books.
          const booksResponse = await axios.get(`http://localhost:8080/api/user/books/`);

          if(booksResponse.status==200){
            //Get all the books whose price is in the selected range.
            const priceRangeResponse = await axios.get(`http://localhost:8080/api/user/books/pricerange/${priceRange.min}/${priceRange.max}`);
            const allBooks = booksResponse.data;
            const booksInPriceRange = priceRangeResponse.data;
            // Find the intersection of all books and the books in price range.
            const newBooks = allBooks.filter(book1=>{
              return booksInPriceRange.some(book2=>{
                return book1.id==book2.id;
              })
            })
            setBooks(newBooks);
            setIsLoading(false);
          }
        }
        else{
          setIsLoading(true);
          // When genre is selected get the books specific to that genre.
          const genreResponse = await axios.get(`http://localhost:8080/api/user/books/genre/${genre}`);

          if(genreResponse.status==200){
            // Get all the books whose price is in the selected range.
            const priceRangeResponse = await axios.get(`http://localhost:8080/api/user/books/pricerange/${priceRange.min}/${priceRange.max}`);
            const genreBooks = genreResponse.data;
            const booksInPriceRange = priceRangeResponse.data;
            // Find the intersection of genre specific books and the books in price range.
            const newBooks = genreBooks.filter(book1=>{
              return booksInPriceRange.some(book2=>{
                return book1.id==book2.id;
              })
            })
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
      const res = await axios.put(url,book1);
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
      <Sidebar setGenre = {setGenre} setPriceRange = {setPriceRange}/>

      (<div className='books'>
        <LoadingComponent isLoading={isLoading}/>
        {books.length>0?(
          <>
            <div className='books-grid'>
              {
                books.map((book)=>
                  <Book 
                    key={book.id} 
                    isInCart = {isInCart(book.id)} 
                    cartId = {findInCart(book.id)} 
                    book = {book} 
                    deleteBook = {deleteBook} 
                    editBook = {editBook} 
                    cartItems = {cartItems} 
                    setCartItems = {setCartItems}
                  />
                )
              }
            </div>
          </>) :
        ((!isLoading) && <img src='/no-search-found.png'></img>)}
      </div>)
    </> 
  )
}

export default Books
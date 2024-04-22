import React, { useEffect, useState } from 'react'
import Book from '../book/Book';
import './Books.css'
import Sidebar from '../sidebar/Sidebar';
import axios from 'axios';
import { toast } from 'react-toastify';
import LoadingComponent from '../Loading/LoadingComponent';
import { useSelector } from 'react-redux';

function Books({books,setBooks,cartItems,setCartItems}) {
  const user = useSelector(state=>state);
  const [genre,setGenre] = useState('');
  const [subscribedBooks,setSubscribedBooks] = useState();
  const [availability,setAvailability] = useState();
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
            let newBooks = allBooks.filter(book1=>{
              return booksInPriceRange.some(book2=>{
                return book1.id==book2.id;
              })
            })
            if(availability===true){
              newBooks = newBooks.filter((book)=>book.availableQuantity>0);
            }
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
            let newBooks = genreBooks.filter(book1=>{
              return booksInPriceRange.some(book2=>{
                return book1.id==book2.id;
              })
            })
            if(availability===true){
              newBooks = newBooks.filter((book)=>book.availableQuantity>0);
            }
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
  },[genre,priceRange,availability])

  useEffect(()=>{
    async function getSubscriptions(){
      const res = await axios.get('http://localhost:8080/api/user/subscription/getSubscriptions/'+user.name);
      setSubscribedBooks(res.data)
    }
    getSubscriptions();
  },[])

  function isSubscribed(id){
    const book = subscribedBooks.find((subscribedBook)=>{
        return subscribedBook.book.id==id;
    }
    );
    if(book) return true;
    return false;
  }
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
  async function editBook(id,book1,shouldSendNotification){
    try{
      const url = 'http://localhost:8080/api/admin/books/' + id;
      const res = await axios.put(url,book1);
      if(shouldSendNotification){
        const res = await axios.post('http://localhost:8080/api/user/notification/dispatchBookStockRefillNotfications',
        null,
        {params : {bookname : book1.title}});
      }
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
      <Sidebar setGenre = {setGenre} setPriceRange = {setPriceRange} setAvailability = {setAvailability}/>

      (<div className='books'>
        <LoadingComponent isLoading={isLoading}/>
        {books.length>0?(
          <>
            <div className='books-grid'>
              {subscribedBooks &&
                books.map((book)=>{
                  // Finding whether the book is already marked for notification.
                  const issubscribed = isSubscribed(book.id);
                  return <Book 
                    key={book.id} 
                    isInCart = {isInCart(book.id)} 
                    cartId = {findInCart(book.id)} 
                    book = {book} 
                    deleteBook = {deleteBook} 
                    editBook = {editBook} 
                    cartItems = {cartItems} 
                    setCartItems = {setCartItems}
                    prevIsSubscribed = {issubscribed}
                  />
                }
                
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
import React, { useContext, useDeferredValue, useEffect, useState, useTransition } from 'react';
import './Nav.css';
import {Link} from 'react-router-dom'
import Modal from '../modal/Modal';
import axios from 'axios';
import { IoCartOutline } from "react-icons/io5";
import { CgProfile } from "react-icons/cg";
import { CiLogout } from "react-icons/ci";
import { BiBookAdd } from "react-icons/bi";
import { IoLibrary } from "react-icons/io5";
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import {Button, FormControl,Paper,Stack,TextField,Select,MenuItem, InputLabel} from '@mui/material'
function Nav({books,setBooks}) {    const [searchBy,setSearchBy] = useState('book');
    const [isOpen,setIsOpen] = useState(false);
    const [search,setSearch] = useState('');
    const [bookNameError,setBookNameError] = useState(false);
    const [bookNameErrorMessage,setBookNameErrorMessage] = useState('');
    const [bookPriceError,setBookPriceError] = useState(false);
    const [bookPriceErrorMessage,setBookPriceErrorMessage] = useState('');
    const [bookAvailableQuantityError,setbookAvailableQuantityError]=useState(false);
    const [bookAvailableQuantityErrorMessage,setbookAvailableQuantityErrorMessage] = useState('');
    const actualSearch = useDeferredValue(search);
    const user = useSelector(state=>state);
    const dispatch = useDispatch();
    const initialState = {
        title : '',
        url : '',
        author : '',
        genre : '',
        price : '',
        availableQuantity : 1
    }
    const [newBook,setNewBook] = useState(initialState);
    useEffect(()=>{
        handleSearch(actualSearch)
    },[actualSearch])
    useEffect(()=>{
        setBookNameError(false);
        setBookNameErrorMessage('');
        setBookPriceError(false);
        setBookPriceErrorMessage('');
        setbookAvailableQuantityError(false);
        setbookAvailableQuantityErrorMessage('')
    },[isOpen])
    function validateBook(){
        let flag=true;
        if(newBook.title === ''){
            setBookNameError(true);
            setBookNameErrorMessage('Book name cannot be empty');
            flag=false;
        }
        if(newBook.price === ''){
            setBookPriceError(true);
            setBookPriceErrorMessage('Price must be given');
            flag=false;
        }
        if(newBook.availableQuantity === ''){
            setbookAvailableQuantityError(true);
            setbookAvailableQuantityErrorMessage('Quantity must be given');
            flag=false;
        }
        if(parseFloat(newBook.price)<0){
            setBookPriceError(true);
            setBookPriceErrorMessage('Invalid price');
            flag=false;
        }
        if(parseFloat(newBook.availableQuantity)<0){
            setbookAvailableQuantityError(true);
            setbookAvailableQuantityErrorMessage('Invalid quantity');
            flag=false;
        }
        return flag;
    }
    async function handleAdd(e){
        setBookNameError(false);
        setBookNameErrorMessage('')
        setBookPriceError(false);
        setBookPriceErrorMessage('');
        setbookAvailableQuantityError(false);
        setbookAvailableQuantityErrorMessage('');
        e.preventDefault();
        if(!validateBook()){
            return;
        }
        try{
            e.preventDefault();
            setNewBook(initialState);
            const res = await axios.post('http://localhost:8080/api/admin/books/',newBook);
            setBooks([...books,res.data]);
            setIsOpen(false)
        }
        catch(e){
            toast.error((e?.response?.data?.message) || (e.message));
        }
    }
    async function handleSearch(curSearch){
        try{
            if(curSearch.length>0){

                if(searchBy=='book'){
                    const res = await axios.get(`http://localhost:8080/api/user/books/title/${curSearch}`);
                    if(res.status == 200){
                        console.log('api call made');
                        setBooks(res.data)
                    }
                }
                else{
                    const res = await axios.get(`http://localhost:8080/api/user/books/author/${curSearch}`);
                    if(res.status == 200){
                        console.log('api call made');
                        setBooks(res.data)
                    }
                }
            }
            else{
                const res = await axios.get('http://localhost:8080/api/user/books/');
                console.log('api call made');
                setBooks(res.data);
            }
            
        }
        catch(e){
            toast.error((e?.response?.data?.message) || (e.message));
        }
    
    }

  return (
    <div className='nav'>
        <div>
        
        <Link to={'/home'} id='site-name'><IoLibrary className='nav-img'/><span>Book Store</span></Link>
        </div>
        
       
        <div className='search-container'>
            <input type="text" placeholder='Search ...' className='search-bar' value={search} onChange={(e)=>{
                setSearch(e.target.value);
               }}/>


            <p className='search-by-text'>Search by : </p>
            <select name="" id="search-by" value={searchBy} onChange={(e)=>setSearchBy(e.target.value)}>
                <option value="book">Book</option>
                <option value="author">Author</option>
            </select>
        </div>
       
        <div className='right-component'>
        {(user.role === 'customer') && <Link to={'/home/orders'} state={{username : user.name}} className='my-orders'>My orders</Link>}
        {(user.role === 'admin') && <Link to={'/home/users'} className='my-orders'>Manage orders</Link>}
            {(user.role === 'admin') && <BiBookAdd className='nav-img' onClick={()=>setIsOpen(true)}/>}
            {(user.role === 'customer'|| user.role==="user") && <Link to={'/home/cart'}><IoCartOutline className='nav-img'/></Link>}
            {user.role === 'customer' && <Link to={'/home/profile'}><CgProfile className='nav-img' /></Link>}
            <Link to={'/'}><CiLogout className='nav-img' onClick={()=>{dispatch({type : 'logout'})}} /></Link> 
        </div>
        {(isOpen) &&
            <Modal setIsOpen = {setIsOpen}>
                <form className='add-book-form'>
                    <Stack spacing={3}> 
                        <TextField label='Book name' required error={bookNameError} helperText = {bookNameErrorMessage} className='add-book-input' value={newBook.title}  onChange={(e)=>{setNewBook({...newBook,title : e.target.value})}}/>
                        <TextField label = 'Author' className='add-book-input' value={newBook.author} onChange={(e)=>{setNewBook({...newBook,author : e.target.value})}}/>
                        <TextField label='Genre'  className='add-book-input' value={newBook.genre} onChange={(e)=>setNewBook({...newBook,genre : e.target.value})}/>
                        <TextField label='Book image url'   className='add-book-input' value={newBook.url} onChange={(e)=>setNewBook({...newBook,url : e.target.value})}/>
                        <TextField label='Price' required error={bookPriceError} helperText = {bookPriceErrorMessage} className='add-book-input' value={newBook.price} onChange={(e)=>setNewBook({...newBook,price : e.target.value})}/>
                        <TextField label='Quantity' error={bookAvailableQuantityError} helperText={bookAvailableQuantityErrorMessage}  className='add-book-input' value={newBook.availableQuantity} onChange={(e)=>setNewBook({...newBook,availableQuantity : e.target.value})}/>
                        <Button variant='contained' type='submit' className='buy-button' onClick={(e)=>{handleAdd(e);}}> Add </Button>
                    </Stack>
                    
                </form>
            </Modal>
        }
        
    </div>
  )
}

export default Nav
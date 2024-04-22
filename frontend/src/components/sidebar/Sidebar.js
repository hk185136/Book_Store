import React, { useState } from 'react'
import './Sidebar.css';
function Sidebar({setGenre,setPriceRange,setAvailability}) {
  const [isChecked,setIsChecked] = useState(false);
  function handleAvailabilityChange(){
    setIsChecked(prev=>!prev);
    setAvailability(prev=>!prev);
  }
    return (
    <div className='sidebar'>
        <h2 className='heading-filter'>Genre</h2>
        <div className='grid-filter'>
            <label htmlFor="All">All</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('')}/>
            <label htmlFor="horror">Horror</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('horror')}/>
            <label htmlFor="">Romance</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('romance')}/>
            <label htmlFor="">Thriller</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('thriller')}/>
            <label htmlFor="">Fantasy</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('fantasy')}/>
            <label htmlFor="">Historical</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('historical')}/>
            <label htmlFor="">Mystery</label>
            <input type="radio" className='checkbox' name='genre' onChange={()=>setGenre('mystery')}/>
        </div>

        <h2 className='heading-filter'>Price</h2>
        <div className='grid-filter'>

          <label htmlFor="">All</label>
          <input type="radio" className='checkbox' name='price' onChange={()=>
          {
            setPriceRange({min : 0,max:20000});
          }}/>

          <label htmlFor="">Under &#8377;1000</label>
          <input type="radio" className='checkbox' name='price' onChange={()=>
          {
            setPriceRange({min : 0,max:1000});
          }}/>

          <label htmlFor="">&#8377;1000 - &#8377;5000</label>
          <input type="radio" className='checkbox' name='price' onChange={()=>
          {
            setPriceRange({min : 1000,max:5000});
          }}/>

          <label htmlFor="">&#8377;5000 - &#8377;20,000</label>
          <input type="radio" className='checkbox' name='price' onChange={()=>
          {
            setPriceRange({min : 5000,max:20000});
          }}/>

        </div>
        <h2 className='heading-filter'>Stock</h2>
        <div className='grid-filter'>
          <label htmlFor="">In stock only</label>
          <input type="checkbox" checked = {isChecked} onChange={handleAvailabilityChange}/>
        </div>

    </div>
  )
}

export default Sidebar
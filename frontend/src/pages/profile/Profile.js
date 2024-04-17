import React, { useEffect, useState } from 'react'
import './Profile.css'
import Modal from '../../components/modal/Modal';
import axios from 'axios';
import HIstoryItem from '../../components/HistoryItem/HIstoryItem';
import { getHistory } from '../../HIstory';
import { useSelector } from 'react-redux';
import { toast } from 'react-toastify';
function Profile() {
  const user=useSelector(state=>state);
  const [pno,setPno] = useState(user.pno || '');
  const [address,setAddress] = useState(user.address || '');
  const [pnoInput,setPnoInput] = useState(user.pno || '');
  const [addressInput,setAddressInput] = useState(user.address || '');
  const [isOpen,setIsOpen] = useState(false);
  const [history,setHistory] = useState([]);

  useEffect(()=>{
    async function get(){
    const hist = await getHistory(user.name);
    setHistory(hist);
    }
    get()
  },[])

  async function editProfile(e){
    e.preventDefault(); 
    const body = {
      username:user.name,
      pno:pnoInput,
      address:addressInput
    };
    setIsOpen(false);
    try{
      const res= await axios.put(`http://localhost:8080/api/auth/editUser/${user.name}`,body);
      setAddress(addressInput);
      setPno(pnoInput);

    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }

  }
  function handleDelete(id){
    try{
      axios.delete('http://localhost:8080/api/user/orderhistory/'+id);
      const newHistory = history.filter(item=>item.id!=id);
      setHistory(newHistory);
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  function clearHistory(){
    try{
      axios.delete('http://localhost:8080/api/user/orderhistory/delete/'+user.name);
      setHistory([])
    }
    catch(e){
      toast.error((e?.response?.data?.message) || (e.message));
    }
  }
  return (
    <div className='profile-page-container'>

      <div className='profile-page'>
        <p>Username</p>
        <p>-</p>
        <p>{user.name}</p>
        <p>Phone No.</p>
        <p>-</p>
        <p>{(pno.length>0)?pno:"No contact info"}</p>
        <p>Delivery address</p>
        <p>-</p>
        <p>{(address.length>0)?address:"No address info"}</p>
        <button className='buy-button' style={{fontSize:'large'}} onClick={()=>{setIsOpen(true);setAddressInput(address);setPnoInput(pno)}}>Edit</button>
      </div>

      {(isOpen) && <Modal setIsOpen = {setIsOpen}>
        <form action="" style={{fontSize : 'larger'}}>
        <p>Phone No.</p>
        <input type="text" className='profile-pno' value={pnoInput} onChange={(e)=>setPnoInput(e.target.value)}/>
        <p>Delivery address</p>
        <textarea className='profile-addr' type="text" rows={3} value={addressInput} onChange={(e)=>setAddressInput(e.target.value)}/>
        <br />
        <button className='buy-button' style={{fontSize:'large'}}  onClick={(e)=>editProfile(e)}>Edit profile</button>
        </form>
      </Modal>}

      <div className='history'> 
        {history.length>0 && 
        <>
        <button className='clear-history' onClick={clearHistory}>Clear history</button>
          <table className='table'>
            <tr>
              <th>Time</th>
              <th>Activity</th>
            </tr>
            {history.map((historyItem) => <HIstoryItem key={historyItem.id} history = {historyItem} handleDelete={handleDelete}/>)}
          </table>
        </>}
      </div>
      
    </div>
    
    
  )
}

export default Profile
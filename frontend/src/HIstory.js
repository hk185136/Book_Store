import axios from "axios"

export const getHistory = async (username)=>{
    try{
        console.log("called getHistory")
        const res = await axios.get(`http://localhost:8080/api/user/orderhistory/getOrderHistory/${username}`);
        console.log(res.data);
        return res.data;
    }
    catch(e){
        alert(e.message);
    }
}

export const pushHistory = async (item) =>{
    try{
        axios.post(`http://localhost:8080/api/user/orderhistory/addToHistory`,item);
    }
    catch(e){
        alert(e.message);   
    }
}
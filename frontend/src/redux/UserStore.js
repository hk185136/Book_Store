import {createStore} from 'redux';
const reducer = (state=null,action)=>{
    switch(action.type){
        case 'login' : return action.data;
        case 'logout' : return null;
        case 'update' : return {...state,...action.data};
        default : return state;
    }
}
export const User = createStore(reducer);


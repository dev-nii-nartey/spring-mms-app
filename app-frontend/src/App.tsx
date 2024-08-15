import {BrowserRouter as Router} from 'react-router-dom'
import './App.css'
import Auth from "./component/Auth.tsx";


export const apiBaseUrl = import.meta.env.VITE_API_BASE_URL;

function App() {

  return (
    <Router>
     <Auth/>
    </Router>
  )
}

export default App

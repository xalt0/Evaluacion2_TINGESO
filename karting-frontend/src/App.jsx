import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import ExtraHoursList from './components/ExtraHoursList';
import AddEditExtraHours from './components/AddEditExtraHours';
import NotFound from './components/NotFound';
import PaycheckList from './components/PaycheckList';
import PaycheckCalculate from './components/PaycheckCalculate';
import AnualReport from './components/AnualReport';
import KartList from './components/KartList';
import AddEditKart from './components/AddEditKart';
import UserList from './components/UserList';
import AddEditUser from './components/AddEditUser';
import ReserveList from './components/ReserveList';
import AddEditReserve from './components/AddEditReserve';
import Rack from './components/Rack';

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/karts/list" element={<KartList/>} />
              <Route path="/karts/add" element={<AddEditKart/>} />
              <Route path="/karts/edit/:id" element={<AddEditKart/>} />
              <Route path="/users/list" element={<UserList/>} />
              <Route path="/users/add" element={<AddEditUser/>} />
              <Route path="/users/edit/:id" element={<AddEditUser/>} />
              <Route path="/reserves/list" element={<ReserveList/>} />
              <Route path="/reserves/add" element={<AddEditReserve/>} />
              <Route path="/reserves/edit/:id" element={<AddEditReserve/>} />
              <Route path="/rack" element={<Rack/>} />
              <Route path="/paycheck/list" element={<PaycheckList/>} />
              <Route path="/paycheck/calculate" element={<PaycheckCalculate/>} />
              <Route path="/reports/AnualReport" element={<AnualReport/>} />
              <Route path="/extraHours/list" element={<ExtraHoursList/>} />
              <Route path="/extraHours/add" element={<AddEditExtraHours/>} />
              <Route path="/extraHours/edit/:id" element={<AddEditExtraHours/>} />
              <Route path="*" element={<NotFound/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App

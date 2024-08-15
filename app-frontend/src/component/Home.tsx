// components/Home.tsx
import React from 'react';
import {Col, Container, Row} from "react-bootstrap";
import {Route, Routes} from "react-router-dom";
import Transactions from "./Transactions.tsx";
import ItemsInventory from "./ItemsInventory.tsx";
import Customers from "./Customers.tsx";
import Logout from "./Logout.tsx";
import NavBar from "./NavBar.tsx";
import Auth from "./Auth.tsx";
import Users from "./Users.tsx";

const Home: React.FC = () => {
    return (
        <Container fluid>
        <Row>
        <Col md={3} lg={2} className="p-0">
            <NavBar/>
        </Col>

        <Col md={9} lg={10} className="p-3"> <Routes>
            <Route path="/dashboard" element={<Users/>}/>
            <Route path="/transactions" element={<Transactions/>}/>
            <Route path="/items-inventory" element={<ItemsInventory/>}/>
            <Route path="/customers" element={<Customers/>}/>
            <Route path="/" element={<Auth/>}/>
            <Route path="/logout" element={<Logout/>}/>
        </Routes>
        </Col>
    </Row>
        </Container>)


};

export default Home;


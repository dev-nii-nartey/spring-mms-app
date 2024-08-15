import {Nav, Navbar} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import {FaCopy, FaHome, FaListUl, FaSignOutAlt, FaUser} from "react-icons/fa";

function NavBar() {
    return (

        <Navbar bg="dark" variant="dark" className="d-flex flex-column" style={{ minWidth: '200px',maxWidth:'700px', height: '100vh' }}>
            <Navbar.Brand className="text-center mt-3 mb-3">
                <h3>LIGALI</h3>
            </Navbar.Brand>
            <Nav className="flex-column">
                <LinkContainer to="/dashboard">
                    <Nav.Link >
                        <FaHome className="mr-2" />
                        Home
                    </Nav.Link>
                </LinkContainer>
                <LinkContainer to="/transactions">
                    <Nav.Link>
                        <FaCopy className="mr-2" />
                        Transactions
                    </Nav.Link>
                </LinkContainer>
                <LinkContainer to="/items-inventory">
                    <Nav.Link>
                        <FaListUl className="mr-2" />
                        Items Inventory
                    </Nav.Link>
                </LinkContainer>
                <LinkContainer to="/customers">
                    <Nav.Link>
                        <FaUser className="mr-2" />
                        Customers
                    </Nav.Link>
                </LinkContainer>
                <LinkContainer to="/logout">
                    <Nav.Link>
                        <FaSignOutAlt className="mr-2" />
                        Logout
                    </Nav.Link>
                </LinkContainer>
            </Nav>
        </Navbar>
    );
}

export default NavBar;
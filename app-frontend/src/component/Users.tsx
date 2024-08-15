import DataTable from "./DataTable.tsx";

const Users = () => {
    const columns = ['Id', 'Name', 'Email', 'Phone'];
    const data = [
        { id: 1, name: 'John Jane', email: 'sam@example.com', phone: '123-456-7890' },
        { id: 2, name: 'Doe Smith', email: 'mas@example.com', phone: '098-765-4321' },
    ];

    return (
        <>
            <h1>Users</h1>
            <DataTable columns={columns} data={data} />
        </>
    );
};
export default Users;
// components/DataTable.tsx
import React from 'react';
import {Table, Button} from 'react-bootstrap';

interface DataTableProps {
    columns: string[];
    data: any[];
    actions?: boolean;
}

const DataTable: React.FC<DataTableProps> = ({columns, data, actions = true}) => {
    return (
        <Table bordered hover>
            <thead>
            <tr>
                {columns.map((column, index) => (
                    <th key={index}>{column}</th>
                ))}
                {actions && <th>Action</th>}
            </tr>
            </thead>
            <tbody>
            {data.map((row, rowIndex) => (
                <tr key={rowIndex}>
                    {columns.map((column, colIndex) => (
                        <td key={colIndex}>{row[column.toLowerCase()]}</td>
                    ))}
                    {actions && (
                        <td>
                            <Button variant="primary" size="sm">Edit</Button>{' '}
                            <Button variant="danger" size="sm">Delete</Button>
                        </td>
                    )}
                </tr>
            ))}
            </tbody>
        </Table>
    );
};

export default DataTable;
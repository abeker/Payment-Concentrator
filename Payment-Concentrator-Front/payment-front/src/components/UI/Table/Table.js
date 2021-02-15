import React, {useState} from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import classNames from './Table.module.css';

const BasicTable = (props) => {
  const createData = (id, paymentDate, amount) => {
      return { id, paymentDate, amount };
  }

  console.log(props.content);
  const rows = [];
  props.content.forEach((element, index) => {
    rows.push(createData(index+1, element.paymentDate, element.membership.amount));
  });
  if(rows.length === 0) {
    rows.push(createData("-", "-", "-"));
  }

  return (
    <TableContainer className={ classNames.Container } component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <StyledTableCell>#</StyledTableCell>
            <StyledTableCell align="center">Payment Date</StyledTableCell>
            <StyledTableCell align="center">Amount</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
              <TableCell component="th" scope="row">
                {row.id}
              </TableCell>
              <TableCell align="center">{row.paymentDate}</TableCell>
              <TableCell align="center">{row.amount} €</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

const StyledTableCell = withStyles((theme) => ({
    head: {
      backgroundColor: theme.palette.common.black,
      color: theme.palette.common.white,
    },
    body: {
      fontSize: 18,
    },
  }))(TableCell);

export default BasicTable;
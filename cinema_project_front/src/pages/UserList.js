import {useEffect, useState} from "react";
import {deleteUser, getAllUsers} from "../service/fetch";
import {Box, Grid, Paper, Typography} from "@mui/material";
import * as React from "react";
import Button from "@mui/material/Button";

const UserList = () => {

    const [users, setUsers] = useState([]);

    const deleteUserFromDatabase = (user_id) => {
        deleteUser(user_id).then( (res) => {
            if( res.status === 200 )
                res.json().then( (res) => {console.log("Deleted: " + res); getAllUsersFromDatabase();} )
        } )
    };

    const getAllUsersFromDatabase = () => {
        getAllUsers().then( res => {
            if(res.status === 200)
                res.json().then( val => setUsers(val) );
        })
    }


    useEffect( () => {
        getAllUsersFromDatabase();
    }, [])


    return (
        <Box sx={{backgroundColor:'white', paddingY:2, paddingX:0.5}}>
            <Paper sx={{backgroundColor : 'black', textAlign : 'center', paddingY : 1, color : '#fff', borderRadius:0}}>
                <Typography> List of all users in database </Typography>
            </Paper>

            <Grid container>
                {users.map( (user, num) => (
                    <Grid item key={`user_${num}`} xs={12}>
                        <Paper sx={{backgroundColor : 'secondary.main', minHeight:'80px', marginTop:2}}>
                            <Box sx={{width:'100%'}}>
                                <Button
                                    sx={{backgroundColor:'primary.main', color:'white', float:'right', margin:2}}
                                    onClick={ () => {deleteUserFromDatabase(user.id)} }
                                    size="small"
                                > delete </Button>
                            </Box>
                            <Typography sx={{padding:2}}>
                                Login: {user.login} <br/>
                                Mail: {user.mail} <br/>
                                Phone: {user.phone}
                            </Typography>
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
};

export default UserList;
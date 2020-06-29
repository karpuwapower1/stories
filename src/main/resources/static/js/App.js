import axios from 'axios';
const React = require('react'); 
const ReactDOM = require('react-dom'); 

class App extends React.Component {

	constructor(props) {
		console.log("constructor");
		super(props);
		this.state = {users: []};
	}

	componentDidMount() {
		axios.get('/user{/id}').done(response => {
			this.setState({users: response.entity._embedded.users});
		});
	}

	render() {
		return (
			<UserList users={this.state.users}/>
		)
	}
}


class UserList extends React.Component{
	render() {
		const users = this.props.users.map(user =>
			<User key={user._links.self.href} user={user}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
					</tr>
					{user}
				</tbody>
			</table>
		)
	}
}

class User extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.user.firstName}</td>
				<td>{this.props.user.lastName}</td>
			</tr>
		)
	}
}

ReactDOM.render(
		<App />,
		document.getElementById('app')
	)
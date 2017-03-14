/**
 * Created by jean.h.ma on 3/14/17.
 */
import React,{Component} from 'react'
import {
	View,
	Text
} from 'react-native'
import alivePush,{AlivePushStatus} from 'react-native-alive-push'

@alivePush({
	deploymentKey:"2a2b1bbefc9450b556c034dd86fd3ab2",
	host:"http://172.16.30.87:8080/"
})
export default class ForceUpgrade extends Component {
	constructor(props){
		super(props);
		this.state={
			status:null
		}
	}
	alivePushStatusChange(status){
		this.setState(Object.assign({},this.state,{
			status
		}));
	}
	render() {
		return (
			<View style={{flexDirection:'column',alignItems:'center',flex:1,justifyContent:'center'}}>
				<Text>Force Upgrade</Text>
				<Text>{this.state.status}</Text>
			</View>
		);
	}
}
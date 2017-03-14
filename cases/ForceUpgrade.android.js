/**
 * Created by jean.h.ma on 3/14/17.
 */
import React,{Component} from 'react'
import {
	View,
	Text,
	Alert
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
			status:null,
			err:"",
			progress:""
		}
	}
	alivePushStatusChange(status,restart){
		this.setState(Object.assign({},this.state,{
			status
		}));
		if(status===AlivePushStatus.afterDownload && restart){
			Alert.alert(
				'更新',
				'更新包已下载好,请重启进行更新!',
				[
					{text:'确定',onPress:()=>{restart();}}
				],
				{ cancelable: false }
			);
		}
	}

	alivePushDownloadProgress(received, total){
		this.setState(Object.assign({},this.state,{
			progress:received/total
		}));
	}

	alivePushError(err){
		this.setState(Object.assign({},this.state,{
			err:err.toString()
		}));
	}

	render() {
		return (
			<View style={{flexDirection:'column',alignItems:'center',flex:1,justifyContent:'center'}}>
				<Text>检查到有更新能够正确下载,重启之后能够使用更新包进行安装</Text>
				<Text>{this.state.status}</Text>
				<Text>{this.state.progress}</Text>
				<Text>{this.state.err}</Text>
			</View>
		);
	}
}
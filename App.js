/**
 * Created by jean.h.ma on 3/14/17.
 */
import React, {Component} from 'react'
import {
	View,
	Text,
	Alert,
	NativeModules
} from 'react-native'
import alivePush, {AlivePushStatus} from './alive-push'

const {RNAlivePush} = NativeModules;

class App extends Component {
	constructor(props) {
		super(props);
		this.state = {
			status: [],
			err: "",
			progress: ""
		}
		this.status = Object.keys(AlivePushStatus).map(k => ({
			key: k,
			value: AlivePushStatus[k]
		}));
	}

	getStatusText(value) {
		if (this.state.status) {
			const status = this.status.find(f => f.value === value);
			if (status) {
				return status.key;
			}
			return '';
		}
		return '';
	}

	alivePushStatusChange(status, packageInfo) {
		console.log(packageInfo)
		let newState = Object.assign({}, this.state);
		newState.status.push({
			text: this.getStatusText(status),
			value: status
		});
		this.setState(newState, () => {
			if (status === AlivePushStatus.afterDownload) {
				Alert.alert(
					'更新',
					'更新包已下载好,请重启进行更新!',
					[
						{
							text: '确定', onPress: () => {
								RNAlivePush.restart();
							}
						}
					],
					{cancelable: false}
				);
			}
		});

	}

	alivePushDownloadProgress(received, total) {
		this.setState(Object.assign({}, this.state, {
			progress: received / total
		}));
	}

	alivePushError(err) {
		this.setState(Object.assign({}, this.state, {
			err: err.toString()
		}));
	}

	render() {
		return (
			<View style={{flexDirection: 'column', alignItems: 'center', flex: 1, justifyContent: 'center'}}>
				<Text>检查到有更新能够正确下载,重启之后能够使用更新包进行安装</Text>

				<Text>progress:{this.state.progress}</Text>
				<Text>error:{this.state.err}</Text>
				{this.state.status.map((item, index) => {
					return <Text key={index}>status:{item.text}:{item.value}</Text>
				})}
			</View>
		);
	}
}

export default alivePush({
	deploymentKey: "b1ca9e0955b8d48ded51549586c066ff",
	host: "http://172.16.30.236:8080/"
})(App);
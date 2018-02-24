/**
 * Created by jean.h.ma on 3/14/17.
 */
import React, {Component} from 'react'
import {
    View,
    Text,
    Alert,
    NativeModules,
    Image,
    Platform
} from 'react-native'
import alivePush, {AlivePushStatus} from 'react-native-alive-push'

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
        let newState = Object.assign({}, this.state);
        newState.status.push({
            text: this.getStatusText(status),
            value: status
        });
        this.setState(newState, () => {
            // if (status === AlivePushStatus.afterDownload) {
            // 	Alert.alert(
            // 		'更新',
            // 		'更新包已下载好,请重启进行更新!',
            // 		[
            // 			{
            // 				text: '确定', onPress: () => {
            // 					RNAlivePush.restart();
            // 				}
            // 			}
            // 		],
            // 		{cancelable: false}
            // 	);
            // }
        });

    }

    alivePushDownloadProgress(received, total) {
        this.setState(Object.assign({}, this.state, {
            progress: received / total
        }));
    }

    alivePushError(err) {
        debugger
        this.setState(Object.assign({}, this.state, {
            err: err.toString()
        }));
    }

    render() {
        return (
            <View style={{flexDirection: 'column', flex: 1}}>
                <Image style={{width: 50, height: 50}} source={require('./assets/1.png')}></Image>
                <Text>当前进度:{this.state.progress}</Text>
                <Text>错误消息:{this.state.err}</Text>
                <Text>状态变化:</Text>
                {this.state.status.map((item, index) => {
                    return <Text style={{marginLeft: 20}}
                                 key={index}>{item.text} : {item.value}</Text>
                })}
            </View>
        );
    }
}

export default alivePush({
    deploymentKey: Platform.OS === "android" ? "1229e43400d972b7349c5f7932718f9c" : "b1ca9e0955b8d48ded51549586c066ff",
    host: "http://172.16.30.236:8080/"
})(App);
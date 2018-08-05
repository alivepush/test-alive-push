/**
 * Created by jean.h.ma on 3/14/17.
 */


import React, {Component} from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Alert,
    Image,
    Dimensions,
    Platform,
    StatusBar,
    ScrollView,
    NativeModules,
    NativeEventEmitter
} from 'react-native';
import alivePush, {AlivePushStatus} from 'react-native-alive-push'

const windowsSize = Dimensions.get('window');

const {RNAlivePush} = NativeModules;

 const AlivePushEmitter = new NativeEventEmitter(RNAlivePush);

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            info: "",
            message: []
        }
    }

    /**
     * alive push 状态改变回调方法
     * @param {AlivePushStatus} status - 状态
     * @param {ResponseJSON} packageInfo - 更新包信息
     * */
    alivePushStatusChange(status, packageInfo) {

        switch (status) {

            /**开始检查前**/
            case AlivePushStatus.beforeCheck : {
                this.appendMessage(`准备开始检查版本信息`)
                break;
            }
            /**正在检查服务器上是否有当前版本对应的更新包**/
            case AlivePushStatus.checking : {
                this.appendMessage(`正在检查版本信息...`)
                break;
            }
            /**更新检查完成，如果有更新packageInfo中的data字段将包含更新包的信息，如果没有更新，则packageInfo中的data字段为null**/
            case AlivePushStatus.afterCheck : {
                if (packageInfo && packageInfo.success) {
                    if (packageInfo.data) {

                        this.appendMessage(`检查完成,更新包信息:${JSON.stringify(packageInfo.data)}`);
                    }
                    else {
                        this.appendMessage(`检查完成,没有需要更新的包`);
                    }
                }
                else {
                    this.appendMessage(`检查完成,发生错误`);
                }
                break;
            }
            /**准备下载更新包**/
            case AlivePushStatus.beforeDownload :
                break;
            /**正在下载更新包到本地**/
            case AlivePushStatus.downloading : {
                this.appendMessage(`正在下载安装包...`);
                break;
            }
            /**更新包下载完成**/
            case AlivePushStatus.afterDownload : {
                this.appendMessage(`下载完成`);
                break;
            }
            /**新版本安装成功，并成功启动**/
            case AlivePushStatus.install : {
                this.appendMessage(`新版本安装成功`);
                break;
            }
            default:
        }

    }

    /**
     * 更新包下载进度回调方法
     * @param {number} received - 已经下载的包大小
     * @param {number} total - 更新包总的大小
     * */
    alivePushDownloadProgress(received, total) {
        this.appendMessage(`下载进度${(received / total).toFixed(2)}%`)
    }

    appendMessage(message) {
        this.setState({
            message: [...this.state.message, message]
        });
    }

    /**
     * 更新出错回调方法
     * @param {object} err - 已经下载的包大小
     * */
    alivePushError(err) {
        debugger
        this.appendMessage(err.toString());
    }


    render() {
        return (
            <View style={{flex: 1, backgroundColor: '#ECECEC'}}>
                <StatusBar
                    backgroundColor="blue"
                    barStyle="light-content"
                />
                <View style={{
                    backgroundColor: 'white',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: Platform.OS === "ios" ? 64 : 54,
                    width: windowsSize.width,
                    flexDirection: "row"
                }}>
                    <Image source={require('./assets/icon.png')} style={{width: 50, height: 50}}/>
                    <Text style={{
                        color: 'black',
                        fontWeight: 'bold',
                        fontSize: 20,
                        marginTop: Platform.OS === "ios" ? 20 : 0
                    }}>Test Alive Push</Text>
                </View>
                <View style={{flexDirection: "row"}}>
                    <Text style={styles.button} onPress={() => {
                        this.appendMessage(`重新加载bundle`);
                        RNAlivePush.reloadBundle();
                    }}>Reload Bundle</Text>
                </View>
                <ScrollView style={{flex: 1}}>
                    {this.state.message.map((m, i) => {
                        return (
                            <Text style={{textAlign: 'center', color: '#333'}} key={i}>{m}</Text>
                        );
                    })}
                </ScrollView>
            </View>
        );
    }

    componentDidMount() {
        AlivePushEmitter.addListener(RNAlivePush.EVENT_BUNDLE_LOAD_ERROR, (err) => {
            this.appendMessage(`bundle加载失败 # ${err}`);
        })
    }
}

export default alivePush({
    deploymentKey: "ccdd1a41f914afbe16797db8c3a9843a",
    host: "https://www.alivepush.com/",
    debug: false
})(App);

const styles = StyleSheet.create({
    button: {
        padding: 10,
        backgroundColor: "black",
        color: "white"
    }
})
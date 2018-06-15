/**
 * Created by jean.h.ma on 3/14/17.
 */
/**
 * Created by yzw on 2018/5/31.
 */

import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    Alert,
    Image,
    Dimensions,
    Platform,
    StatusBar
} from 'react-native';
import alivePush, {AlivePushStatus} from 'react-native-alive-push'

const windowsSize = Dimensions.get('window');


class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            info:""
        }
    }

    /**
     * alive push 状态改变回调方法
     * @param {AlivePushStatus} status - 状态
     * @param {ResponseJSON} packageInfo - 更新包信息
     * */
    alivePushStatusChange(status, packageInfo) {

        switch(status){

            /**开始检查前**/
            case AlivePushStatus.beforeCheck : {
                return this.setState({info:"准备中..."})
            };
            /**正在检查服务器上是否有当前版本对应的更新包**/
            case AlivePushStatus.checking : {
                return this.setState({info:"正在检查版本信息..."})
            };
            /**更新检查完成，如果有更新packageInfo中的data字段将包含更新包的信息，如果没有更新，则packageInfo中的data字段为null**/
            case AlivePushStatus.afterCheck : {
                if(packageInfo && packageInfo.success){
                    if(packageInfo.data){
                        return this.setState({info:"检查完成，需要更新"});
                    }
                    else{
                        return this.setState({info:"检查完成，没有需要更新的包"});
                    }
                }
                else{
                    return this.setState({info:"检查失败"});
                }
            }
            /**准备下载更新包**/
            case AlivePushStatus.beforeDownload :
            /**正在下载更新包到本地**/
            case AlivePushStatus.downloading : {
                return this.setState({info:"开始下载安装包..."})
            };
            /**更新包下载完成**/
            case AlivePushStatus.afterDownload : {
                if(alivePush.restart){
                    alivePush.restart();
                }
                return this.setState({info:"下载完成"})
            };
            /**新版本安装成功，并成功启动**/
            case AlivePushStatus.install : {
                Alert.alert("新版本启动成功")
            };

            default:{};
        }

    }

    /**
    * 更新包下载进度回调方法
     * @param {number} received - 已经下载的包大小
     * @param {number} total - 更新包总的大小
    * */
    alivePushDownloadProgress(received, total) {

        this.setState(Object.assign({}, this.state, {
            info: "下载进度"+(received / total).toFixed(2)+'%'
        }));
    }


    /**
     * 更新出错回调方法
     * @param {object} err - 已经下载的包大小
     * */
    alivePushError(err) {
        this.setState(Object.assign({}, this.state, {
            info: err.toString()
        }));
    }



    render() {
        return (
            <View style={{flex:1,backgroundColor:'#ECECEC'}}>

                <StatusBar
                    backgroundColor="blue"
                    barStyle="light-content"
                />
                <View style={{backgroundColor:'#232e3C',justifyContent:'center',alignItems:'center',height:Platform.OS === "ios"?64:54,width:windowsSize.width}}>
                    <Text style={{color:'#FFF',fontWeight:'bold',fontSize:20,marginTop:Platform.OS === "ios"?20:0}}>Test Alive Push</Text>
                </View>
                <View style={{flex:1,justifyContent:'center',alignItems:'center'}}>
                    <View style={{marginBottom:200}}>
                        <Image source={require('./assets/icon.png')}/>
                        <Text style={{textAlign:'center',color:'#333'}}>{this.state.info}</Text>
                    </View>
                </View>

            </View>
        );
    }
}

export default alivePush({
    deploymentKey: "b858f0319e404d16d5a46fb6dcf229da",
    host: "http://47.98.165.10:8080/",
    debug: "development"
})(App);
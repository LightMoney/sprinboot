package cn.fan.config;


import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @Author: xinzhifu
 * @Description:
 */
@Configuration
public class IotMqttProducerConfig {

    @Autowired
    private MqttConfig mqttConfig;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options=new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttConfig.getServers()});
//        factory.setServerURIs(mqttConfig.getServers());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel iotMqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "iotMqttInputChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttConfig.getServerClientId(), mqttClientFactory());
        messageHandler.setAsync(false);
        messageHandler.setDefaultQos(2);
        messageHandler.setDefaultTopic(mqttConfig.getDefaultTopic());
        return messageHandler;
    }
}

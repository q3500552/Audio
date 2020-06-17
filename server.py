from websocket_server import WebsocketServer
import json
from pyaudio import PyAudio, paInt16

# Called for every client connecting (after handshake)
def new_client(client, server):
    print("New client connected and was given id %d" % client['id'])



# Called for every client disconnecting
def client_left(client, server):
    print("Client(%d) disconnected" % client['id'])
    

import base64
p=PyAudio()
stream=p.open(
        format=paInt16,
        channels=1,
        rate=8000,
        output=True,
        output_device_index=2
        )
# Called when a client sends a message
def message_received(client, server, message):
    print("Client(%d) said: %s" % (client['id'], message[:5]))
    stream.write(base64.b64decode(message))
    
    
PORT=9001
server = WebsocketServer(PORT, "0.0.0.0")
server.set_fn_new_client(new_client)
server.set_fn_client_left(client_left)
server.set_fn_message_received(message_received)
server.run_forever()

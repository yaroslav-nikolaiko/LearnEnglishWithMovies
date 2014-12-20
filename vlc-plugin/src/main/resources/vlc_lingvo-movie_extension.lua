-- time-lite.lua -- VLC extension --
--[[
INSTALLATION:
Put the file in the VLC subdir /lua/extensions, by default:
* Windows (all users): %ProgramFiles%\VideoLAN\VLC\lua\extensions\
* Windows (current user): %APPDATA%\VLC\lua\extensions\
* Linux (all users): /usr/share/vlc/lua/extensions/
* Linux (current user): ~/.local/share/vlc/lua/extensions/
* Mac OS X (all users): /Applications/VLC.app/Contents/MacOS/share/lua/extensions/
(create directories if they don't exist)
Restart the VLC.
Then you simply use the extension by going to the "View" menu and selecting it.
--]]


function descriptor()
	return {
		title = "Time (lite)";
		version = "1.0";
		author = "Yaroslav";
		url = 'http://addons.videolan.org/content/show.php?content=149619';
		shortdesc = "Lingvo-Movie";
		description = "<div style=\"background-color:lightgreen;\"><b>Time (lite)</b> is VLC extension (extension script \"time-lite.lua\") that displays running time on the screen in a playing video.</div>";
		capabilities = {"input-listener", "playing-listener"}
	}
end
function activate()
	input_callback("add")
	vlc.var.add_callback(vlc.object.input(), "position", position_change)
end
function deactivate()
	vlc.osd.message("")
	input_callback("del")
	vlc.var.del_callback(vlc.object.input(), "position", position_change)
end
function input_changed()
	input_callback("toggle")
end

callback=false
function input_callback(action)  -- action=add/del/toggle
	if (action=="toggle" and callback==false) then action="add"
	elseif (action=="toggle" and callback==true) then action="del" end

	local input = vlc.object.input()
	if input and callback==false and action=="add" then
		callback=true
		vlc.var.add_callback(input, "intf-event", input_events_handler, "Hello world!")
	elseif input and callback==true and action=="del" then
		callback=false
		vlc.var.del_callback(input, "intf-event", input_events_handler, "Hello world!")
	end
end


function input_events_handler(var, old, new, data)
	--vlc.osd.message(tostring(var)..'\n'..tostring(old)..'\n'..tostring(new)..'\n'..tostring(data))
	local systemTime = os.date("%H:%M:%S") -- reads and formats OS time

	local input = vlc.object.input()
	--get current playback time --
	local elapsed_time = vlc.var.get(input, "time")
	--vlc.osd.message(elapsed_time) -- displays time on the screen in a video
	--vlc.msg.info(systemTime)
end

function playing_changed()
   -- related to capabilities={"playing-listener"} in descriptor()
   -- triggered by Pause/Play madia input event
   extract_time()
end

function position_change(var, old, new, data)
   extract_time()
end

function extract_time()
   local input = vlc.object.input()
   local elapsed_time = vlc.var.get(input, "time")
   --vlc.osd.message(elapsed_time)
   execute_request(elapsed_time)
end

-- ********************************************* HTTP REQUESTS *******************************************--

function test()
   get("http://tevatron.ddns.ukrtel.net/lingvo-movie/rest/live")
end

function execute_request(current_time)
	local url = "http://tevatron.ddns.ukrtel.net/lingvo-movie-core/rest/live/"..current_time
   get(url)
end

function get(url)
   local host, path, option = parse_url(url)
   local header = {
      "GET "..path.." HTTP/1.1",
      "Host: "..host,
      "User-Agent: ".."SomeCrap",
      "",
      ""
   }
   local request = table.concat(header, "\r\n")

   local response
   local status, response = http_req(host, 80, request)

   if status == 200 then
      return response
   else
      return false
   end
end

function http_req(host, port, request)
   local fd = vlc.net.connect_tcp(host, port)
   if fd >= 0 then
      local pollfds = {}

      pollfds[fd] = vlc.net.POLLIN
      vlc.net.send(fd, request)
      vlc.net.poll(pollfds)

      local response = vlc.net.recv(fd, 1024)
      local headerStr, body = string.match(response, "(.-\r?\n)\r?\n(.*)")
      local header = parse_header(headerStr)
      local contentLength = tonumber(header["Content-Length"])
      local TransferEncoding = header["Transfer-Encoding"]
      local status = tonumber(header["statuscode"])
      local bodyLenght = string.len(body)
      local pct = 0

      if status ~= 200 then return status end

      while contentLength and bodyLenght < contentLength do
         vlc.net.poll(pollfds)
         response = vlc.net.recv(fd, 1024)

         if response then
            body = body..response
         else
            vlc.net.close(fd)
            return false
         end
         bodyLenght = string.len(body)
         pct = bodyLenght / contentLength * 100
         --setMessage(openSub.actionLabel..": "..progressBarContent(pct))
      end
      vlc.net.close(fd)

      return status, body
   end
   return ""
end

function parse_header(data)
   local header = {}

   for name, s, val in string.gfind(data, "([^%s:]+)(:?)%s([^\n]+)\r?\n") do
      if s == "" then header['statuscode'] =  tonumber(string.sub (val, 1 , 3))
      else header[name] = val end
   end
   return header
end

function parse_url(url)
  local url_parsed = vlc.net.url_parse(url)
  return  url_parsed["host"],
    url_parsed["path"],
    url_parsed["option"]
end
